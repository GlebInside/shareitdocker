<h1 align="center">Shareitdocker</h1>

<p align="center">
  
<img src="https://img.shields.io/badge/made%20by-glebinside-blue.svg" >

<img src="https://img.shields.io/badge/java-18-orange.svg">

<img src="https://img.shields.io/github/languages/top/glebinside/shareitdocker.svg">

</p>

## Description

**Docker containers**
![scr 2022-11-10 20 54 45](https://user-images.githubusercontent.com/95642615/201172750-e7787c45-8531-434b-a0fe-ea6fa15ec7b3.png)

Backend for a service that works with the booking of things. The application implements a micro service architecture and is divided into two services: containing the main business logic and database server and a lightweight gateway that performs all the validation of requests

## How to use

### The application has only a backend implemented, so you can check its functionality using, for example, postman:
1. Launch shareitdocker_db_1 from the <img src="https://github.com/devicons/devicon/blob/master/icons/docker/docker-original.svg" title="docker" alt="docker" width="40" height="40"/>&nbsp;
2. Launch the ShareItServer in the main method
3. Launch the ShareItGateway in the main method
4. Launch <img src="https://img.shields.io/badge/postman-orange.svg"> and send requests


## About the project

### Share it

The service provides users, firstly, with the opportunity to tell what things they are ready to share, and secondly, to find the right thing and rent it for a while.
The service not only allows you to book a thing for certain dates, but also to close access to it at the time of booking from other people. In case there is no necessary thing on the service, users have the opportunity to leave requests


