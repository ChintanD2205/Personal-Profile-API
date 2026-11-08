INSERT INTO dbo.ROLES (RoleName, Description) VALUES
('MARKETING_AGENT', 'Can access customer data for marketing campaigns'),
('COMPLIANCE_OFFICER', 'Can view audit logs and consent reports'),
('ADMIN', 'Full system access including privacy requests'),
('ANALYTICS_AGENT', 'Can access anonymized customer analytics data'),
('PROFILE_MANAGER', 'Can update customer profiles and preferences');

ALTER TABLE dbo.PERMISSIONS
ALTER COLUMN RequiredFields VARCHAR(MAX) NULL;

ALTER TABLE dbo.PERMISSIONS
ALTER COLUMN OptionalFields VARCHAR(MAX) NULL;


INSERT INTO dbo.PERMISSIONS (PermissionName, Description, Resource, PurposeName, RequiredFields, OptionalFields, Action) VALUES

('VIEW_CUSTOMER_PROFILES', 'Can view customer profile data', '/api/profiles', 'ACCOUNT_MANAGEMENT', 'email,firstName,lastName', 'phoneNumber,address', 'READ'),

('ACCESS_MARKETING_DATA', 'Can access marketing-consented customers', '/api/profiles/marketing', 'MARKETING_EMAILS', 'email', 'firstName,lastName', 'READ'),

('VIEW_AUDIT_LOGS', 'Can view system audit trails', '/api/audit', 'SYSTEM_AUDIT', NULL, NULL, 'READ'),

('PROCESS_PRIVACY_REQUESTS', 'Can approve/deny privacy requests', '/api/privacy-requests', 'PRIVACY_ADMINISTRATION', 'email,firstName,lastName', 'phoneNumber,address', 'UPDATE'),

('ACCESS_ANALYTICS_DATA', 'Can access anonymized analytics data', '/api/analytics', 'ANALYTICS_TRACKING', 'behavioralData,sessionData', 'preferences,deviceInfo', 'READ'),

('UPDATE_CUSTOMER_PROFILES', 'Can modify customer profile data', '/api/profiles', 'ACCOUNT_MANAGEMENT', 'email,firstName,lastName', 'phoneNumber,address', 'UPDATE'),

('VIEW_CONSENT_REPORTS', 'Can generate consent compliance reports', '/api/reports/consent', 'COMPLIANCE_REPORTING', NULL, NULL, 'READ'),

('ACCESS_FRAUD_DATA', 'Can access customer data for fraud prevention', '/api/profiles/fraud', 'FRAUD_PREVENTION', 'email,ipAddress', 'phoneNumber,deviceInfo,address', 'READ'),

('SEND_MARKETING_CAMPAIGNS', 'Can execute marketing email campaigns', '/api/campaigns/send', 'MARKETING_EMAILS', 'email', 'firstName,lastName', 'EXECUTE'),

('DELETE_CUSTOMER_DATA', 'Can process erasure requests', '/api/profiles/delete', 'PRIVACY_ADMINISTRATION', 'email', 'firstName,lastName,phoneNumber,address', 'DELETE');

INSERT INTO dbo.ROLE_PERMISSIONS (RoleID, PermissionID) VALUES
(1, 1),
(1, 2),
(2, 3), 
(2, 7), 
(3, 1), 
(3, 3),
(3, 4), 
(3, 6), 
(3, 7),
(4, 5),
(5, 1), 
(5, 6); 


SELECT * FROM ROLES;
SELECT * FROM PERMISSIONS;
SELECT * FROM ROLE_PERMISSIONS;