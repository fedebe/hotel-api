create table hotel_search (
	id bigint generated by default as identity, 
	ages int array not null, 
	check_in date not null, 
	check_out date not null, 
	hotel_id varchar(255) not null, 
	search_id varchar(255) not null, 
	primary key (id));
