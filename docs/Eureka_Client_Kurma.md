Mikroservisleri Eureka ile Kaydetme (Eureka İstemcisi)

**Eureka İstemcisi Nedir?**
  • Keşfedilebilir olmak isteyen her mikroservisin bir Eureka İstemcisi olarak hareket etmesi gerekir.
  • Mikroservis uygulamanızın içine gömülü bir kütüphanedir.
  • Eureka Sunucusu/Sunucuları ile etkileşimden sorumludur.

**İstemci Sorumlulukları:**

1. **Kayıt Olma (Registration):**
    * Uygulama başlangıcında, istemci kendini kaydetmek için Eureka Sunucusu ile iletişime geçer.
    * Kendisi hakkında aşağıdaki gibi meta verileri sağlar:
        * Servis Adı (örn. `product-service`, `order-service`) - genellikle `spring.application.name` üzerinden alınır.
        * Örnek ID'si (Bu spesifik örnek için benzersiz bir tanımlayıcı).
        * Ana Bilgisayar Adı (Hostname) ve Port.
        * Sağlık Kontrolü URL'si (`/actuator/health` yaygındır).
        * Durum Sayfası URL'si, Ana Sayfa URL'si vb.

2. **Yenileme (Renewal - Kalp Atışları):**
    * Kayıttan sonra, istemci Eureka Sunucusuna periyodik olarak "kalp atışı" gönderir (varsayılan olarak genellikle her 30 saniyede bir).
    * Bu, sunucuya örneğin hala hayatta ve sağlıklı olduğunu bildirir.
    * Sunucu, yapılandırılabilir bir çıkarma zaman aşımı süresi içinde kalp atışı almazsa, örneği kayıt defterinden kaldırır.

3. **Kayıt Defterini Getirme (Fetching the Registry):**
    * İstemci, periyodik olarak Eureka Sunucusundan kayıt defteri bilgilerini alır ve yerel olarak önbelleğe alır.
    * Diğer servisleri keşfetmek için istemciler bu yerel önbelleği kullanır.
    * Bu önbellekleme mekanizması dayanıklılığı (istemciler Eureka sunucusu geçici olarak kapalı olsa bile servisleri keşfedebilir) ve performansı (Eureka sunucusundaki yükü azaltır) artırır.

4. **Kayıt Silme (De-registration):**
    * Düzgün kapatılma sırasında (graceful shutdown), istemci Eureka Sunucusuna bir kayıt silme isteği göndererek kendisini aktif örnekler listesinden kaldırır.

**Bir Mikroservisi Eureka İstemcisi Olarak Yapılandırma Adımları:**

1. **Bağımlılıkları Ekleyin:**
    * Mikroservisinizin `pom.xml` veya `build.gradle` dosyasına `spring-cloud-starter-netflix-eureka-client` ekleyin.

    *Maven Örneği (`pom.xml`):*
        ```xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        ```
2. **Ana Uygulama Sınıfında Keşif İstemcisini Etkinleştirin:**
    * Ana Spring Boot uygulama sınıfınıza `@EnableDiscoveryClient` (daha genel) veya `@EnableEurekaClient` (Eureka'ya özgü) ekleyin.
        `@EnableDiscoveryClient` genellikle diğer keşif sistemlerini de desteklediği için tercih edilir.

            *Java Örneği:*
            ```java
            import org.springframework.boot.SpringApplication;
            import org.springframework.boot.autoconfigure.SpringBootApplication;
            import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

            @SpringBootApplication
            @EnableDiscoveryClient
            public class ProductServiceApplication {
                public static void main(String[] args) {
                    SpringApplication.run(ProductServiceApplication.class, args);
                }
            }
            ```

3. **`application.yml` (veya `application.properties`) Dosyasını Yapılandırın:**
    * **Uygulama Adı:** Servisiniz Eureka'da bu şekilde tanımlanacaktır.
            ```yaml
            spring:
            application:
                name: product-service # Veya order-service, vb.
            ```
    * **Eureka Sunucu URL(leri):** İstemciye Eureka Sunucusunun nerede olduğunu bildirin.
            ```yaml
            eureka:
            client:
                serviceUrl:
                defaultZone: http://localhost:8761/eureka/ # Eureka Sunucunuzun URL'si
                # registerWithEureka: true (varsayılan) - bu örneği Eureka'ya kaydet
                # fetchRegistry: true (varsayılan) - Eureka'dan kayıt defteri bilgilerini al
            ```
        Birden fazla Eureka sunucusu (eşler) için virgülle ayrılmış bir liste sağlarsınız.

**Doğrulama:**
  • İstemci uygulaması başladığında, Eureka Sunucusu panelinde görünmelidir.
  • Durumunu göreceksiniz (genellikle STARTING, sonra UP).
