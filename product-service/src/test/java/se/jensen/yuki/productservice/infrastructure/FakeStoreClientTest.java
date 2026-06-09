package se.jensen.yuki.productservice.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import se.jensen.yuki.productservice.model.Rating;
import se.jensen.yuki.productservice.web.dto.ProductDTO;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

class FakeStoreClientTest {
    private MockWebServer mockWebServer;
    private FakeStoreClient fakeStoreClient;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        // Point WebClient at the mock server instead of the real FakeStore API
        WebClient webClient = WebClient.builder()
                .baseUrl(mockWebServer.url("/").toString())
                .build();

        // Patch the URI calls to use relative paths — see note below
        fakeStoreClient = new FakeStoreClient(webClient);

        // Inject the mock server URL via reflection
        ReflectionTestUtils.setField(fakeStoreClient, "baseUrl", mockWebServer.url("/").toString());

        objectMapper = new ObjectMapper();
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    // ── fetchProducts ────────────────────────────────────────────────────────

    @Test
    void fetchProducts_returnsListOfProducts_whenApiRespondsOk() throws Exception {
        // Arrange
        List<ProductDTO> expected = List.of(
                makeProduct(1, "Shirt", 29.99),
                makeProduct(2, "Pants", 49.99)
        );
        mockWebServer.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(expected))
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

        // Act
        List<ProductDTO> result = fakeStoreClient.fetchProducts();

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).id()).isEqualTo(1L);
        assertThat(result.get(1).title()).isEqualTo("Pants");
    }

    @Test
    void fetchProducts_throwsRuntimeException_whenApiReturns5xx() {
        // Arrange
        mockWebServer.enqueue(new MockResponse().setResponseCode(500));

        // Act & Assert
        assertThatThrownBy(() -> fakeStoreClient.fetchProducts())
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("FakeStore API error");
    }

    @Test
    void fetchProducts_throwsRuntimeException_whenApiReturns4xx() {
        // Arrange
        mockWebServer.enqueue(new MockResponse().setResponseCode(404));

        // Act & Assert
        assertThatThrownBy(() -> fakeStoreClient.fetchProducts())
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("FakeStore API error");
    }

    // ── getProductById ───────────────────────────────────────────────────────

    @Test
    void getProductById_returnsProduct_whenApiRespondsOk() throws Exception {
        // Arrange
        ProductDTO expected = makeProduct(1, "Shirt", 29.99);
        mockWebServer.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(expected))
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

        // Act
        ProductDTO result = fakeStoreClient.getProductById(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.title()).isEqualTo("Shirt");
        assertThat(result.price()).isEqualTo(29.99);
    }

    @Test
    void getProductById_throwsRuntimeException_whenApiReturns5xx() {
        // Arrange
        mockWebServer.enqueue(new MockResponse().setResponseCode(500));

        // Act & Assert
        assertThatThrownBy(() -> fakeStoreClient.getProductById(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("FakeStore API error");
    }

    @Test
    void getProductById_throwsRuntimeException_whenApiReturns4xx() {
        // Arrange
        mockWebServer.enqueue(new MockResponse().setResponseCode(404));

        // Act & Assert
        assertThatThrownBy(() -> fakeStoreClient.getProductById(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("FakeStore API error");
    }

    @Test
    void getProductById_returnsNull_whenBodyIsEmpty() {
        // Arrange
        mockWebServer.enqueue(new MockResponse()
                .setBody("null")
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

        // Act
        ProductDTO result = fakeStoreClient.getProductById(99L);

        // Assert — current code does not null-check, so null is returned
        assertThat(result).isNull();
    }

    // ── helper ───────────────────────────────────────────────────────────────

    private ProductDTO makeProduct(Integer id, String title, double price) {
        ProductDTO dto = new ProductDTO(id, title, price, "A description", "category", "image.jpg", new Rating(4.5, 100));
        return dto;
    }
}