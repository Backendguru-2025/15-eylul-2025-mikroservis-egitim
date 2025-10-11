Eureka Sunucusunu Kurma

Eureka Sunucusunun Amacı:

* Merkezi bir Servis Kayıt Defteri olarak görev yapar.
* Mikroservisler (Eureka İstemcileri) kendilerini bu sunucuya kaydeder.
* Diğer servisler, eşlerinin (peer) ağ konumlarını bulmak için bu sunucuyu sorgular.

Temel Bir Eureka Sunucusu Kurma Adımları:

1. Yeni Bir Spring Boot Projesi Oluşturun:
    * Spring Initializr (start.spring.io) kullanın.
    * Tercih ettiğiniz derleme aracını (Maven/Gradle) ve dili (Java/Kotlin) seçin.
2. Bağımlılıkları Ekleyin:
    * Ana bağımlılık spring-cloud-starter-netflix-eureka-server dır.
    * Bu başlangıç (starter) paketi, bir Eureka Sunucusu için gerekli tüm kütüphaneleri içerir.
    * Maven Örneği (pom.xml):
           ```xml
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
            </dependency>
            ```
    * Bağımlılık yönetiminizde Spring Cloud BOM'unun içe aktarıldığından emin olun

3. Ana Uygulama Sınıfında Eureka Sunucusunu Etkinleştirin:
    * Ana Spring Boot uygulama sınıfınıza @EnableEurekaServer anotasyonunu ekleyin.
    * Java Kodu :
            ```java
            import org.springframework.boot.SpringApplication;
            import org.springframework.boot.autoconfigure.SpringBootApplication;
            import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

            @SpringBootApplication
            @EnableEurekaServer
            public class EurekaServerApplication {
                public static void main(String[] args) {
                    SpringApplication.run(EurekaServerApplication.class, args);
                }
            }
            ```
4. application.yml (veya application.properties) Dosyasını Yapılandırın
    * Port: Eureka Sunucusu için bir port ayarlayın (varsayılan 8761'dir).
            ```yaml
            server:
                port: 8761
            ````
    * İstemci Davranışını Devre Dışı Bırakma (tek başına çalışan bir sunucu için)
        * Varsayılan olarak, bir Eureka sunucusu aynı zamanda bir istemcidir ve başka bir Eureka sunucusuna (eşleme için) kaydolmaya çalışır.
        * Tek başına çalışan, bağımsız bir sunucu için bu genellikle devre dışı bırakılır.
                ```yaml
                eureka:
                client:
                    registerWithEureka: false # Kendini bir Eureka sunucusuna kaydetme
                    fetchRegistry: false      # Kayıt defteri bilgilerini almaya çalışma
                # instance: (isteğe bağlı: gerekirse ana bilgisayar adını yapılandırın)
                #   hostname: localhost
                ```
        * Eşler (peer) kuruluyorsa, bunlar true olur ve serviceUrl.defaultZone eşleri gösterir.

Eureka Sunucusu Paneli (Dashboard)

* Eureka Sunucusu çalışmaya başladıktan sonra paneline erişebilirsiniz
* Genellikle "http://<eureka-sunucu-hostu>:<port>/" adresinde bulunur (örn. "http://localhost:8761/" ).
* Panel şunları gösterir
        *Genel sunucu bilgileri
        * O anda kayıtlı servis örnekleri
        * Örneklerin durumu (UP, DOWN, STARTING, vb.).
