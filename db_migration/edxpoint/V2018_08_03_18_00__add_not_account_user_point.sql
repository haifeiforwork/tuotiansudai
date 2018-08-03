insert ignore into user_point(login_name, point, updated_time)
select login_name, '0' as 'point', now() as 'updated_time' from aa.user where not exists(select 1 from aa.account where login_name = aa.user.login_name)