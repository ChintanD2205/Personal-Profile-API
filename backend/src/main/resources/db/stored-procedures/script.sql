
-- Step 1: Create Database
USE [master]
GO
CREATE DATABASE [PersonalProfileAPI]
GO

-- Step 2: Create Login and User
USE [master]
GO
CREATE LOGIN sbs_app WITH PASSWORD = 'SBSAppChintan@123'
GO

USE [PersonalProfileAPI]
GO
CREATE USER [sbs_app] FOR LOGIN [sbs_app] WITH DEFAULT_SCHEMA=[dbo]
GO
ALTER ROLE [db_owner] ADD MEMBER [sbs_app]
GO

-- Step 3: Create Tables
USE [PersonalProfileAPI]
GO

CREATE TABLE [dbo].[AUDIT_LOG](
	[AuditID] [int] IDENTITY(1,1) NOT NULL,
	[CustomerUserID] [int] NOT NULL,
	[InternalUserID] [int] NULL,
	[Action] [varchar](250) NOT NULL,
	[ActionTimestamp] [datetime] NOT NULL,
	[Details] [varchar](max) NOT NULL,
	[SessionID] [varchar](250) NULL,
PRIMARY KEY CLUSTERED 
(
	[AuditID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [dbo].[CLIENTS](
	[UserID] [int] IDENTITY(1,1) NOT NULL,
	[Username] [varchar](100) NOT NULL,
	[Password] [varchar](250) NOT NULL,
	[Email] [varchar](250) NOT NULL,
	[FirstName] [varchar](250) NOT NULL,
	[LastName] [varchar](250) NOT NULL,
	[CreatedAt] [datetime] NOT NULL,
	[UpdatedAt] [datetime] NOT NULL,
	[Status] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[UserID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[CONSENT_RECORDS](
	[ConsentID] [int] IDENTITY(1,1) NOT NULL,
	[UserID] [int] NOT NULL,
	[Purpose] [varchar](250) NOT NULL,
	[Granted] [bit] NOT NULL,
	[ConsentTimestamp] [datetime] NOT NULL,
	[WithdrawnAt] [datetime] NULL,
	[ConsentScope] [varchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[ConsentID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [dbo].[INTERNAL_USERS](
	[EmployeeID] [int] IDENTITY(1,1) NOT NULL,
	[Username] [varchar](100) NOT NULL,
	[Password] [varchar](250) NOT NULL,
	[Email] [varchar](250) NOT NULL,
	[FirstName] [varchar](250) NOT NULL,
	[LastName] [varchar](250) NOT NULL,
	[Department] [varchar](250) NOT NULL,
	[CreatedAt] [datetime] NOT NULL,
	[LastLogin] [datetime] NULL,
	[Status] [bit] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[EmployeeID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[PERMISSIONS](
	[PermissionID] [int] IDENTITY(1,1) NOT NULL,
	[PermissionName] [varchar](100) NOT NULL,
	[Description] [varchar](250) NOT NULL,
	[Resource] [varchar](250) NOT NULL,
	[PurposeName] [varchar](100) NOT NULL,
	[RequiredFields] [varchar](max) NULL,
	[OptionalFields] [varchar](max) NULL,
	[Action] [varchar](250) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[PermissionID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [dbo].[PRIVACY_REQUESTS](
	[RequestID] [int] IDENTITY(1,1) NOT NULL,
	[UserID] [int] NOT NULL,
	[RequestType] [varchar](100) NOT NULL,
	[Status] [varchar](250) NOT NULL,
	[RequestedAt] [datetime] NOT NULL,
	[CompletedAt] [datetime] NULL,
	[Notes] [varchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[RequestID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [dbo].[ROLE_PERMISSIONS](
	[RoleID] [int] NOT NULL,
	[PermissionID] [int] NOT NULL
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[ROLES](
	[RoleID] [int] IDENTITY(1,1) NOT NULL,
	[RoleName] [varchar](100) NOT NULL,
	[Description] [varchar](250) NOT NULL,
	[CreatedAt] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[RoleID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[USER_ROLE_ASSIGNMENTS](
	[EmployeeID] [int] NOT NULL,
	[RoleID] [int] NOT NULL,
	[AssignedAt] [datetime] NOT NULL,
	[AssignedBy] [int] NOT NULL
) ON [PRIMARY]
GO

-- Step 4: Add Default Constraints
ALTER TABLE [dbo].[AUDIT_LOG] ADD DEFAULT (getdate()) FOR [ActionTimestamp]
GO
ALTER TABLE [dbo].[CLIENTS] ADD DEFAULT (getdate()) FOR [CreatedAt]
GO
ALTER TABLE [dbo].[CLIENTS] ADD DEFAULT (getdate()) FOR [UpdatedAt]
GO
ALTER TABLE [dbo].[CONSENT_RECORDS] ADD DEFAULT (getdate()) FOR [ConsentTimestamp]
GO
ALTER TABLE [dbo].[INTERNAL_USERS] ADD DEFAULT (getdate()) FOR [CreatedAt]
GO
ALTER TABLE [dbo].[PRIVACY_REQUESTS] ADD DEFAULT (getdate()) FOR [RequestedAt]
GO
ALTER TABLE [dbo].[ROLES] ADD DEFAULT (getdate()) FOR [CreatedAt]
GO
ALTER TABLE [dbo].[USER_ROLE_ASSIGNMENTS] ADD DEFAULT (getdate()) FOR [AssignedAt]
GO

-- Step 5: Add Foreign Key Constraints
ALTER TABLE [dbo].[AUDIT_LOG] WITH CHECK ADD CONSTRAINT [FK_Audit_Client_User] FOREIGN KEY([CustomerUserID])
REFERENCES [dbo].[CLIENTS] ([UserID])
GO
ALTER TABLE [dbo].[AUDIT_LOG] CHECK CONSTRAINT [FK_Audit_Client_User]
GO

ALTER TABLE [dbo].[AUDIT_LOG] WITH CHECK ADD CONSTRAINT [FK_Audit_Internal_User] FOREIGN KEY([InternalUserID])
REFERENCES [dbo].[INTERNAL_USERS] ([EmployeeID])
GO
ALTER TABLE [dbo].[AUDIT_LOG] CHECK CONSTRAINT [FK_Audit_Internal_User]
GO

ALTER TABLE [dbo].[CONSENT_RECORDS] WITH CHECK ADD CONSTRAINT [FK_Consent_User] FOREIGN KEY([UserID])
REFERENCES [dbo].[CLIENTS] ([UserID])
GO
ALTER TABLE [dbo].[CONSENT_RECORDS] CHECK CONSTRAINT [FK_Consent_User]
GO

ALTER TABLE [dbo].[PRIVACY_REQUESTS] WITH CHECK ADD CONSTRAINT [FK_Privacy_User] FOREIGN KEY([UserID])
REFERENCES [dbo].[CLIENTS] ([UserID])
GO
ALTER TABLE [dbo].[PRIVACY_REQUESTS] CHECK CONSTRAINT [FK_Privacy_User]
GO

ALTER TABLE [dbo].[ROLE_PERMISSIONS] WITH CHECK ADD CONSTRAINT [FK_PermissionID_Permission] FOREIGN KEY([PermissionID])
REFERENCES [dbo].[PERMISSIONS] ([PermissionID])
GO
ALTER TABLE [dbo].[ROLE_PERMISSIONS] CHECK CONSTRAINT [FK_PermissionID_Permission]
GO

ALTER TABLE [dbo].[ROLE_PERMISSIONS] WITH CHECK ADD CONSTRAINT [FK_RoleID_Permission] FOREIGN KEY([RoleID])
REFERENCES [dbo].[ROLES] ([RoleID])
GO
ALTER TABLE [dbo].[ROLE_PERMISSIONS] CHECK CONSTRAINT [FK_RoleID_Permission]
GO

ALTER TABLE [dbo].[USER_ROLE_ASSIGNMENTS] WITH CHECK ADD CONSTRAINT [FK_EmployeeID_Assigner] FOREIGN KEY([AssignedBy])
REFERENCES [dbo].[INTERNAL_USERS] ([EmployeeID])
GO
ALTER TABLE [dbo].[USER_ROLE_ASSIGNMENTS] CHECK CONSTRAINT [FK_EmployeeID_Assigner]
GO

ALTER TABLE [dbo].[USER_ROLE_ASSIGNMENTS] WITH CHECK ADD CONSTRAINT [FK_EmployeeID_Assignment] FOREIGN KEY([EmployeeID])
REFERENCES [dbo].[INTERNAL_USERS] ([EmployeeID])
GO
ALTER TABLE [dbo].[USER_ROLE_ASSIGNMENTS] CHECK CONSTRAINT [FK_EmployeeID_Assignment]
GO

ALTER TABLE [dbo].[USER_ROLE_ASSIGNMENTS] WITH CHECK ADD CONSTRAINT [FK_RoleID_Assignment] FOREIGN KEY([RoleID])
REFERENCES [dbo].[ROLES] ([RoleID])
GO
ALTER TABLE [dbo].[USER_ROLE_ASSIGNMENTS] CHECK CONSTRAINT [FK_RoleID_Assignment]
GO

-- Step 6: Create Stored Procedures
CREATE PROCEDURE [dbo].[profile_create_client]
	@Username	VARCHAR(100),
	@Password	VARCHAR(250),
	@Email		VARCHAR(250),
	@FirstName	VARCHAR(250),
	@LastName	VARCHAR(250),
	@Status		BIT=1
AS
BEGIN
	SET NOCOUNT ON;
	IF EXISTS (SELECT 1 FROM CLIENTS WHERE Email = @Email)
	BEGIN
        PRINT 'A client with this email already exists.';
        RETURN;
    END

	INSERT INTO CLIENTS (Username, Password, Email, FirstName, LastName, CreatedAt, UpdatedAt, Status)
    VALUES (@Username, @Password, @Email, @FirstName, @LastName, GETDATE(), GETDATE(), @Status);

    PRINT 'Client inserted successfully.';

    SELECT CAST(SCOPE_IDENTITY() AS INT) AS NewUserId;
END;
GO

CREATE PROCEDURE [dbo].[sp_internal_user_create]
	@Username	VARCHAR(100),
	@Password	VARCHAR(250),
	@Email		VARCHAR(250),
	@FirstName	VARCHAR(250),
	@LastName	VARCHAR(250),
	@Department	VARCHAR(250),
	@LastLogin	DATETIME=NULL,
	@Status		BIT	
AS
BEGIN
	SET NOCOUNT ON;
	IF EXISTS (SELECT 1 FROM INTERNAL_USERS WHERE Email = @Email)
	BEGIN
        PRINT 'A user with this email already exists.';
        RETURN;
    END

	INSERT INTO INTERNAL_USERS (Username, Password, Email, FirstName, LastName, Department, CreatedAt, LastLogin, Status)
    VALUES (@Username, @Password, @Email, @FirstName, @LastName, @Department, GETDATE(), @LastLogin, @Status);

    PRINT 'User inserted successfully.';

    SELECT SCOPE_IDENTITY() AS NewUserId;
END;
GO

CREATE PROCEDURE [dbo].[sp_update_audit]
    @CustomerUserID     INT,
    @Action             VARCHAR(250),
    @Details            VARCHAR(MAX) = NULL,
    @InternalUserID     INT = NULL,
    @SessionID          VARCHAR(250) = NULL
AS
BEGIN
    SET NOCOUNT ON;

    INSERT INTO dbo.AUDIT_LOG (CustomerUserID, Action, Details, InternalUserID, SessionID)
    VALUES (@CustomerUserID, @Action, @Details, @InternalUserID, @SessionID);

    PRINT 'Audited';

    SELECT SCOPE_IDENTITY() AS NewAuditId;
END;
GO

CREATE PROCEDURE [dbo].[sp_update_consent]
	@UserID			INT,
	@Purpose		VARCHAR(250),
	@Granted		BIT,
	@ConsentScope	VARCHAR(MAX)
AS
BEGIN
	SET NOCOUNT ON;

	INSERT INTO dbo.CONSENT_RECORDS (UserID, Purpose, Granted, ConsentScope)
    VALUES (@UserID, @Purpose, @Granted, @ConsentScope);

    PRINT 'Consent Updated';

    SELECT SCOPE_IDENTITY() AS NewConsentId;
END;
GO

CREATE PROCEDURE [dbo].[user_table_creation]
AS
BEGIN
	IF NOT EXISTS(
		SELECT 1 FROM sys.tables WHERE name='CLIENTS' AND type='U'
	)
	BEGIN
	CREATE TABLE CLIENTS (
	UserID		INT				IDENTITY(1,1) PRIMARY KEY,
	Username	VARCHAR(100)	NOT NULL,
	Password	VARCHAR(250)	NOT NULL,
	Email		VARCHAR(250)	NOT NULL,
	FirstName	VARCHAR(250)	NOT NULL,
	LastName	VARCHAR(250)	NOT NULL,
	CreatedAt	DATETIME		NOT NULL	DEFAULT GETDATE(),
	UpdatedAt	DATETIME		NOT NULL	DEFAULT GETDATE(),
	Status		BIT				NOT NULL
	);
	END
	ELSE
	BEGIN
		PRINT 'CLIENT TABLE ALREADY EXISTS';
	END
END
GO

PRINT 'Database setup complete!'