FROM openjdk:25-slim

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]

# 4. Uygulama Portunu Dışa Açın (Expose)
# Konteyner içindeki uygulamanın 8080 portunu dinlediğini belgeler.
EXPOSE 8080

# 5. Başlangıç Komutunu Tanımlayın
# Konteyner başladığında yürütülecek komutu belirtir.
# Bu, Spring Boot uygulamasını 'java -jar' kullanarak çalıştırır.
CMD ["java", "-jar", "app.jar"]