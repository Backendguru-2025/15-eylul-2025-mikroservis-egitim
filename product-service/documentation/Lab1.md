# Uygulamalı Çalışma 2: ProductService'i Dockerize Etme (Türkçe Çeviri)

Hedef:

1. Gün'de oluşturulan ProductService mikroservisini bir Docker imajına paketlemek ve bir konteyner olarak çalıştırmak.

Ön Koşullar:

* Docker Desktop (veya Linux'ta Docker Engine) kurulu ve çalışıyor olmalı.
  * docker --version ve docker ps komutlarıyla doğrulayın.
* 3. Gün'de geliştirilen ProductService proje koduna erişim.
* ProductService uygulamasının çalıştırılabilir bir JAR dosyası olarak derlenmiş olması gerekir.
* Maven kullanıyorsanız: Proje dizininde mvn clean package komutunu çalıştırın. JAR dosyası muhtemelen target/ klasöründe olacaktır.

Laboratuvar Görevlerine Genel Bakış:

1. ProductService proje dizinine gidin.
2. ProductService projesinin kök dizininde bir Dockerfile oluşturun.
3. Dockerfile'a gerekli talimatları (FROM, WORKDIR, COPY, EXPOSE, CMD) ekleyin.
4. (İsteğe Bağlı ama Önerilir) Derleme bağlamından (build context) gereksiz dosyaları hariç tutmak için bir .dockerignore dosyası oluşturun.
5. docker build komutunu kullanarak Docker imajını oluşturun.
6. Oluşturulan imajdan docker run komutunu kullanarak bir Docker konteyneri çalıştırın.
7. Konteynerin çalıştığını ve servisin erişilebilir olduğunu doğrulayın.

(Her görev için ayrıntılı adımlar takip edecektir)

Servisi konteynerleştirelim. Terminalinizi veya komut istemcinizi açın.

1. Proje Dizinine Gidin:

    * ProductService projenizin pom.xml veya build.gradle dosyasının bulunduğu kök dizine gidin.
    * cd /product-service/proje/yolu
2. Dockerfile Oluşturun:
    * Bu dizinde tam olarak Dockerfile adında (uzantısız) yeni bir metin dosyası oluşturun.
    * Slaytlardaki örnek içeriği bu dosyaya yapıştırın.
    * Önemli: COPY komutunun kaynak yolunun, derleme aracınızın JAR dosyasını yerleştirdiği yerle eşleştiğini doğrulayın (örn. Maven için target/, Gradle için build/libs/).
3. (İsteğe Bağlı) .dockerignore Oluşturun:
    * .dockerignore adında yeni bir metin dosyası oluşturun.
    * Derleme bağlamını (build context) küçük tutmak ve daha hızlı derleme yapmak için gereksiz dosya/klasörleri hariç tutmak üzere aşağıdaki gibi satırlar ekleyin:
    `
    .git
    .gitignore
    target/
    build/
    .mvn/
    .gradle/
    /# IDE'ye özgü dosyaları ekleyin: .idea/, *.iml, .vscode/ vb.
    `
4. Docker İmajını Oluşturun (Build):
    * Öncelikle, uygulama JAR dosyanızın derlendiğinden emin olun:
        * Maven: mvn clean package -DskipTests
    * Şimdi, imajı oluşturun (proje kök dizininde olduğunuzdan emin olun):
        * docker build -t product-service:lab .
        * (Not: -t seçeneği imajı product-service olarak ve lab etiketiyle etiketler. Sondaki . geçerli dizini derleme bağlamı olarak belirtir.)
    * Komutun çıktısını izleyin; "Successfully tagged product-service:lab" mesajıyla bitmelidir.
5. Docker Konteynerini Çalıştırın:
    * Yeni imajınızdan bir konteyner başlatın:
        * docker run -d -p 8088:8080 --name product-service-container product-service:lab
    * Açıklama:
        * -d: Ayrık mod (arka planda çalışır).
        * -p 8088:8080: Ana makinenizdeki (host) 8088 portunu, konteyner içindeki 8080 portuna (Spring Boot'un çalıştığı yer) eşler. 8088 kullanmak, ana makinenizde 8080 portu zaten kullanılıyorsa çakışmaları önler.
        * --name product-service-container: Çalışan konteynere hatırlanması kolay bir ad verir.
        * product-service:lab: Çalıştırılacak imaj.
Sonraki Adım: Doğrulama yapacağız.

ProductService konteynerimizin doğru çalışıp çalışmadığını teyit edelim:

1. Çalışan Konteynerleri Kontrol Edin:
    * Terminalinizde docker ps komutunu çalıştırın.
    * Muhtemelen product-service-container adıyla konteynerinizi listede görmelisiniz.
    * STATUS (Durum) bilgisinin "Up ..." (Çalışıyor ...) olduğunu doğrulayın.
    * PORTS (Portlar) sütununun, örn. 0.0.0.0:8088->8080/tcp gibi port eşlemesini gösterdiğini teyit edin.
2. Konteyner Loglarını İnceleyin:
    * docker logs product-service-container komutunu çalıştırın.
    * Komutun çıktısını gözden geçirin. Tanıdık Spring Boot başlangıç mesajlarını, port 8080'de (konteyner içinde) Tomcat'in başlatıldığını belirten satırları ve "Started ProductServiceApplication..." log satırını arayın.
    * Canlı logları görmek için (hata ayıklamada kullanışlıdır): docker logs -f product-service-container (Çıkmak için Ctrl+C tuşlarına basın).
3. API Uç Noktasını (Endpoint) Test Edin:
    * Bir web tarayıcısı, curl, Postman veya benzeri bir araç kullanın.
    * Servise, eşlediğiniz ana makinenin portunu (docker run örneğimizde port 8088) kullanarak erişin.
    * URL: http://localhost:8088/api/v1/products (veya sizin belirlediğiniz özel endpoint yolu)
    * Servisinizden başarılı bir HTTP yanıtı (örn. 200 OK) almalısınız, muhtemelen boş bir JSON dizisi [] veya varsa başlangıç verilerini göstermelidir.
    * Curl Örneği: curl http://localhost:8088/api/v1/products
4. (İsteğe Bağlı Temizlik):
    * Konteyneri durdurmak için: docker stop product-service-container
    * Durdurulmuş konteyneri kaldırmak için: docker rm product-service-container (Şimdilik çalışır durumda bırakabiliriz).

Başarılı! İlk mikroservisinizi Docker kullanarak başarıyla konteynerleştirdiniz ve çalıştırdınız.

