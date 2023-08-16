insert into post_packages(id, receive_index, receiver_address, receiver_name, type)
values (123, 123123, 'address', 'name', 'LETTER');

insert into post_offices(index, name, receiver_address)
values (123123, 'name', 'receiver address');

insert into post_offices(index, name, receiver_address)
values (123124, 'name', 'receiver address');

insert into delivery_history (delivery_status, time, post_office_index, post_package_id)
values ('ARRIVE', to_timestamp('02-02-2020 18-27-00', 'DD-MM-YYYY, HH24-MI-SS'), 123123, 123);