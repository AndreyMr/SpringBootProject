/*устанавливаем пользователя для админа*/
insert into springdb.usr(id, username, password, active) values (1, 'admin', '$2a$08$aIpM/0WX7.eYW.cftVRwi.6/Ph5ZinDHvr5OJypEUqilkdre.b9C6',true);
insert into springdb.user_role(user_id, roles) values (1,'ADMIN'),(1,'USER');