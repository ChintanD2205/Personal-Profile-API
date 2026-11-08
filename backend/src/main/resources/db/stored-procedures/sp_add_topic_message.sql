CREATE OR ALTER PROCEDURE sp_add_topic_message
	@subject	VARCHAR(250),
	@purpose	VARCHAR(250),
	@message	VARCHAR(250),
	@createdBy	INT,
	@TopicARN	VARCHAR(MAX) OUTPUT
AS
BEGIN

	SET NOCOUNT ON;
	DECLARE @TID INT;
	SELECT @TID=topicID FROM TOPIC WHERE purpose=@purpose;

	INSERT INTO TOPIC_MESSAGES (TopicID, Subject, Message, CreatedBy) VALUES (@TID, @subject, @message, @createdBy);

	DECLARE @Details VARCHAR(250);
	SET @Details = 'Published message for Topic ID: ' + CAST(@TID AS VARCHAR(20))
                     + ' for subject: ' + @subject;

	SELECT @TopicARN=topicARN
	FROM TOPIC
	WHERE topicID=@TID;

	EXEC sp_update_audit
		@CustomerUserID = NULL,
		@Action = 'MESSAGE_PUBLISHED',
		@Details = @Details,
		@InternalUserID = @createdBy;
END;
GO

SELECT * FROM TOPIC WHERE purpose='MARKETING_EMAILS';