docker giadung-spring | true
docker rmi spring-boot-app | true

until docker build -t spring-boot-app .
do
    echo "Waiting for build image spring-boot-app..."
    sleep 4
done

docker run -d -p 8080:8080 --name giadung-spring spring-boot-app