UPDATE users
SET password = '$2a$10$dgy078nXhSCdiUBxNu8gk.buN/sw.Qw4EG5tebiEIbU7v2NAar8wq'
WHERE login_id IN ('user', 'admin');
