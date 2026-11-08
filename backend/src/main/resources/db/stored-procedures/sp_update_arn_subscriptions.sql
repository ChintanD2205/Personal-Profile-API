CREATE OR ALTER PROCEDURE sp_update_arn_subscriptions
	@UserId			INT,
	@Purpose		VARCHAR(250),
	@SubscriptionID	VARCHAR(MAX)
AS
BEGIN
	SET NOCOUNT ON;

	IF EXISTS(SELECT 1 FROM ARN_SUBSCRIPTIONS WHERE userID=@UserId AND purpose=@Purpose)
	BEGIN
		IF (@SubscriptionID IS NULL)
		BEGIN
			DELETE FROM ARN_SUBSCRIPTIONS WHERE userID=@UserId AND purpose=@Purpose;
		END
	END
	ELSE
		INSERT INTO ARN_SUBSCRIPTIONS VALUES (@UserId, @Purpose, @SubscriptionID);
END;
GO