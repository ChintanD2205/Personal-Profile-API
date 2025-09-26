CREATE OR ALTER PROCEDURE sp_hard_delete
	@UserID INT,
	@InternalUserID INT,
	@Notes VARCHAR(250)
AS
BEGIN
	SET NOCOUNT ON;
	
	BEGIN TRY
		BEGIN TRANSACTION;
		
		DECLARE @Username VARCHAR(100), @Email VARCHAR(250), @Details VARCHAR(500);
		SELECT @Username = Username, @Email = Email FROM CLIENTS WHERE UserID = @UserID;
		SET @Details = 'User ' + ISNULL(@Username, '') 
                     + ' hard deleted. Notes: ' + ISNULL(@Notes, '');

		EXEC sp_update_audit
			@CustomerUserID = @UserID,
			@Action = 'HARD_DELETE',
			@Details = @Details,
			@InternalUserID = @InternalUserID;
		
		UPDATE ACCESS_LOGS
		SET UserID=NULL WHERE UserID=@UserID;

		UPDATE AUDIT_LOG
		SET CustomerUserID=NULL WHERE CustomerUserID=@UserID;

		DELETE FROM CONSENT_RECORDS WHERE UserID = @UserID;

		UPDATE PRIVACY_REQUESTS
		SET UserID=NULL
		WHERE UserID = @UserID AND CompletedAt is not NULL;

		UPDATE PRIVACY_REQUESTS
		SET Status = 'Completed', 
		    CompletedAt = GETDATE(), 
		    Notes = @Notes,
		    ProcessedBy = @InternalUserID,
			UserID=NULL
		WHERE UserID = @UserID AND CompletedAt is NULL;

		DELETE FROM CLIENTS WHERE UserID = @UserID;
		
		
		
		COMMIT TRANSACTION;
		
		SELECT @Username as DeletedUser, 'Successfully deleted' as Status;
		
	END TRY
	BEGIN CATCH
		ROLLBACK TRANSACTION;
		SELECT @Username as DeletedUser, 'Unsuccessful' as Status;
		THROW;
	END CATCH
END;
GO