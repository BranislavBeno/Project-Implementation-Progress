FROM alpine:latest

# Set local timezone
RUN apk update && apk add --no-cache tzdata && cp -r -f /usr/share/zoneinfo/Europe/Bratislava /etc/localtime

# Install jdk
RUN apk add --no-cache openjdk11

# Copy jar binary
COPY ./build/libs/ImplProgress.jar /app/

# Copy application configuration
COPY ./conf/implementationprogress.properties /app/conf/

# Set working directory
WORKDIR /app

# Set default values for environment variables
ENV APP_USER user
ENV APP_PASSWD passwd

# Run application
CMD /usr/bin/java -jar ImplProgress.jar -u $APP_USER -p $APP_PASSWD -d
