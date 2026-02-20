# 1. 베이스 이미지 (AWS니까 Amazon Corretto 17 추천)
FROM amazoncorretto:17

# 2. 빌드된 jar 파일을 컨테이너 안으로 복사
# (build/libs/*.jar 파일이 딱 하나만 생긴다고 가정)
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# 3. 실행 명령어 (스프링 부트 실행)
ENTRYPOINT ["java", "-jar", "/app.jar"]
