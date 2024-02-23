# TaskTrack

A web application that helps you and your team to share and track tasks.

## Motivation

There are many similar (and more powerful) tools like TaskTrack. But I prefer not to install lots of apps on my laptop and phone. And I really dislike the ads covering parts of my screen, and the fancy-looking UIs.

## Quickstart

I don't have a personal server, so you have to deploy it yourself. Sorry for that.

### Clone this repository

`git clone https://github.com/LGM70/TaskTrack.git`

### Setup backend environment

1. Sign up for [MongoDB Altas](https://www.mongodb.com/atlas) and create a cluster with no charge (if you don't have one).

2. I recommend you to download [IntelliJ IDEA](https://www.jetbrains.com/idea/) to build the backend project.

3. In `backend/src/main/resources`, create a `.env` file. You should follow the syntax of `backend/src/main/resources/.env.example`. The first 4 variable can be found in the link you used to connect the MongoDB database. The last variable needs to be a more-than-64-char-long string.

4. Load the project using maven, and build it in IntelliJ IDEA.

### Setup frontend environment

1. I recommend you to use [VS Code](https://code.visualstudio.com/) to run the frontend part.

2. Download [node.js](https://nodejs.org/en/download) if you don't have one.

3. run `npm install` to install all dependencies.

4. modify `frontend/src/api/axiosConfig.js` if you are not going to deploy it locally.

5. run `npm start` and you will have the TaskTrack running in your browser.

### Another choice

If you are willing to donate some money, I will be happy to rent a server and deploy the TaskTrack for you and your team.

### Usage

I will upload a video to demostrate how to use TaskTrack days later. Feel free to [contact me](https://lgm70.github.io) if you have any questions.

### Frameworks used

The backend APIs are built using SpringBoot in Java, while the frontend application is developed with React.js, Bootstrap and leverages Axios for handling HTTP requests and responses.