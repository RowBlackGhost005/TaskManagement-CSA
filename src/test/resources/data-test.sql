INSERT INTO Users (username, password ) VALUES ('Admin' , 'thisshouldbeencrypted') , ('User' , 'thisshouldbeencrypted2'), ('Another' , 'thisshouldbeencrypted3');

INSERT INTO Tasks (`description`, `due_date`, `priority`, `status`, `title`, `user_id`) VALUES
('Complete project documentation', '2025-06-15 10:30:00', 'HIGH', 'PENDING', 'Documentation', 1),
('Fix authentication bug', '2025-06-10 14:45:00', 'MEDIUM', 'INPROGRESS', 'Bug Fixing', 2),
('Optimize database queries', '2025-06-20 09:00:00', 'HIGH', 'PENDING', 'Performance Tuning', 3),
('Implement user notifications', '2025-06-12 16:00:00', 'LOW', 'COMPLETED', 'Notifications', 1),
('Update API Gateway configuration', '2025-06-25 11:15:00', 'MEDIUM', 'PENDING', 'API Optimization', 3),
('Review unit tests coverage', '2025-06-18 13:30:00', 'HIGH', 'INPROGRESS', 'Testing', 2);