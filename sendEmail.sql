CREATE DATABASE SendEmail
GO
USE SendEmail
GO

CREATE TABLE SendPass
(
	username VARCHAR(50) PRIMARY KEY,
	[password] VARCHAR(50)
)
GO

INSERT INTO dbo.SendPass
(
    username,
    password
)
VALUES
(   'vuddph14036@fpt.edu.vn',  -- username - varchar(50)
    '123' -- password - varchar(50)
    ),
	(   'duc02',  -- username - varchar(50)
    '456' -- password - varchar(50)
    )
	SELECT * FROM dbo.SendPass

	update SendPass set [password] = 123 where username LIKE 'duc01'