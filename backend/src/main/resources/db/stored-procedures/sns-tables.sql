CREATE TABLE TOPIC (
    topicID     INT IDENTITY(1,1) PRIMARY KEY,
    topicARN    VARCHAR(250) NOT NULL,
    topicName   VARCHAR(250) NOT NULL,
    purpose     VARCHAR(250) NOT NULL,
    createdBy   INT NOT NULL
);

CREATE TABLE TOPIC_MESSAGES (
    messageID   INT IDENTITY(1,1) PRIMARY KEY,
    topicID     INT NOT NULL,
    subject     VARCHAR(250) NOT NULL,
    message     VARCHAR(MAX) NULL,
    createdBy   INT NOT NULL,
    createdOn   DATETIME DEFAULT GETDATE(),
    CONSTRAINT FK_TopicMessages_Topic FOREIGN KEY (topicID)
        REFERENCES TOPIC(topicID)
);

CREATE TABLE ARN_SUBSCRIPTIONS (
    arnsubID        INT IDENTITY(1,1) PRIMARY KEY,
    userID          INT NOT NULL,
    purpose         VARCHAR(250) NOT NULL,
    subscriptionId  VARCHAR(MAX) NULL,
    CONSTRAINT FK_ARN_USER FOREIGN KEY (userID) REFERENCES CLIENTS(UserID)
);
