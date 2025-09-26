CREATE OR ALTER PROCEDURE sp_create_topic
	@topicName	VARCHAR(250),
	@arnTopic	VARCHAR(250),
	@purpose	VARCHAR(250),
	@createdBy	INT
AS
BEGIN

	SET NOCOUNT ON;

	INSERT INTO TOPIC VALUES (@arnTopic, @topicName, @purpose, @createdBy);

	DECLARE @Details VARCHAR(250);
	SET @Details = 'Created Topic: ' + @topicName 
                     + ' for ' + @purpose;

	EXEC sp_update_audit
		@CustomerUserID = NULL,
		@Action = 'TOPIC_CREATED',
		@Details = @Details,
		@InternalUserID = @createdBy;
END;
GO