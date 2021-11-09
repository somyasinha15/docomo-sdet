# base image
FROM ubuntu:bionic-20200311

LABEL maintainer="TestFramework"

ENV GRADLE_VERSION 6.9
ENV ALLURE_VERSION 2.14.0

# install packages
RUN apt-get -o Acquire::Check-Valid-Until=false update
RUN apt-get install -y openjdk-11-jdk vim wget curl zip unzip git python-pip python-dev build-essential

# Install Gradle
RUN wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip && \
  unzip gradle-${GRADLE_VERSION}-bin.zip && \
  mv gradle-${GRADLE_VERSION} /opt/ && \
  rm gradle-${GRADLE_VERSION}-bin.zip
ENV GRADLE_HOME /opt/gradle-${GRADLE_VERSION}
ENV PATH $PATH:$GRADLE_HOME/bin

RUN echo "export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64" >> ~/.bashrc

ENV JAVA_HOME="/usr/lib/jvm/java-11-openjdk-amd64/jre"
ENV PATH $JAVA_HOME/bin:$PATH

# Install allure
RUN curl -o allure-commandline-${ALLURE_VERSION}.tgz -Ls https://dl.bintray.com/qameta/maven/io/qameta/allure/allure-commandline/${ALLURE_VERSION}/allure-commandline-${ALLURE_VERSION}.tgz && \
  tar -zxvf allure-commandline-${ALLURE_VERSION}.tgz -C /opt/ && ln -s /opt/allure-${ALLURE_VERSION}/bin/allure /usr/bin/allure && allure --version

# Install Chrome
RUN wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
RUN dpkg -i google-chrome-stable_current_amd64.deb; apt-get -fy install

USER root
