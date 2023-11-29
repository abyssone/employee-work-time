INSERT INTO public.employees (id,birth_date,address,name,sex) VALUES
	 ('ad60d9a5-d82b-44cc-b4f9-c9e2d5ffe47a','1986-03-12','Пензенская область, Пенза, Ульяновская ул., 11','Олег Михайлов',0),
	 ('5423e8a4-0aa9-44df-82ec-c724647ab31d','1991-02-26','Пензенская область, Пенза, ул. Лермонтова, 3к2','Мария Широкова',1),
	 ('25e0c289-eb2c-496a-9e76-b990471a3719','1994-12-22','Пензенская область, Пенза, ул. Лермонтова, 3к2 р-н Ленинский','Вадим Краснов',0),
	 ('023998b4-ad0a-4a02-95a0-c022be3d468e','1983-06-09','г. Пенза, ул Пушкина 2, кв. 17','Максим Кузнецов',0),
	 ('17aa9595-0660-48ee-9deb-c5b0725f69ac','1978-11-11','г. Пенза, ул Пушкина 2, кв. 17','Петр Удальцов',0);

INSERT INTO public.fixed_work_week (id,title) VALUES
	 ('9a63c417-9513-49ed-9565-c4e70211e228','5/2 8:00-17:00');
INSERT INTO public.shift_work_schedule (id,work_days_number,days_off_number,start_work_schedule,start_time,end_time,title) VALUES
	 ('a2110b3f-19d9-4f08-b12d-44b786e44cfb',3,2,'2023-10-23','10:00:00','20:30:00','3/2 10:00-20:30');
INSERT INTO public.week_day_work_hours_mapping (day_of_week,fixed_work_week_id,start_time,end_time) VALUES
	 (1,'9a63c417-9513-49ed-9565-c4e70211e228','08:00:00','17:00:00'),
	 (0,'9a63c417-9513-49ed-9565-c4e70211e228','08:00:00','17:00:00'),
	 (2,'9a63c417-9513-49ed-9565-c4e70211e228','08:00:00','17:00:00'),
	 (4,'9a63c417-9513-49ed-9565-c4e70211e228','08:00:00','17:00:00'),
	 (3,'9a63c417-9513-49ed-9565-c4e70211e228','08:00:00','17:00:00');

INSERT INTO public.contracts (id,date_of_conclusion,expiration_date,"position",work_time_model_id,employee_id) VALUES
	 ('a6a206ce-8c08-45cd-8bf2-cd60cb2901a9','2023-11-01',NULL,'Дворник','9a63c417-9513-49ed-9565-c4e70211e228','ad60d9a5-d82b-44cc-b4f9-c9e2d5ffe47a'),
	 ('2af7c82d-cf08-46b4-9592-99cc0b6f9c66','2023-11-01',NULL,'Зав Хоз','9a63c417-9513-49ed-9565-c4e70211e228','5423e8a4-0aa9-44df-82ec-c724647ab31d'),
	 ('1437ed92-ec73-4a10-933e-4d103b662f6c','2023-11-01','2023-12-29','Раскладывает бумаги из одной кучки в две, а потом обратно','a2110b3f-19d9-4f08-b12d-44b786e44cfb','17aa9595-0660-48ee-9deb-c5b0725f69ac'),
	 ('199051c7-a77b-46b3-b1b5-c88fbee69c9c','2023-11-01',NULL,'Программист, электрик, devOps, системный администратор и просто хороший человек','9a63c417-9513-49ed-9565-c4e70211e228','25e0c289-eb2c-496a-9e76-b990471a3719'),
	 ('e6b1ae3f-d566-4b13-a245-e86ee26ec42e','2023-11-01',NULL,'Предводитель','9a63c417-9513-49ed-9565-c4e70211e228','023998b4-ad0a-4a02-95a0-c022be3d468e');

INSERT INTO public.exceptional_days ("date",start_time,end_time,info) VALUES
	 ('2023-11-10',NULL,NULL,'Какой-то гос. праздник'),
	 ('2023-11-18','06:00:00','18:00:00','дедлайн вчера, а мы еще не начинали');

INSERT INTO public.contracts_to_exceptional_days (exceptional_day_id,contract_id) VALUES
	 (1,'2af7c82d-cf08-46b4-9592-99cc0b6f9c66'),
	 (1,'1437ed92-ec73-4a10-933e-4d103b662f6c'),
	 (1,'e6b1ae3f-d566-4b13-a245-e86ee26ec42e'),
	 (1,'a6a206ce-8c08-45cd-8bf2-cd60cb2901a9'),
	 (1,'199051c7-a77b-46b3-b1b5-c88fbee69c9c'),
	 (2,'1437ed92-ec73-4a10-933e-4d103b662f6c'),
	 (2,'e6b1ae3f-d566-4b13-a245-e86ee26ec42e'),
	 (2,'199051c7-a77b-46b3-b1b5-c88fbee69c9c');

INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-01','09:00:00','17:00:00',1,'199051c7-a77b-46b3-b1b5-c88fbee69c9c');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-02','08:00:00','17:00:00',0,'199051c7-a77b-46b3-b1b5-c88fbee69c9c');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-03','09:00:00','16:00:00',2,'199051c7-a77b-46b3-b1b5-c88fbee69c9c');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-06','10:00:00','17:00:00',0,'199051c7-a77b-46b3-b1b5-c88fbee69c9c');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-07','09:00:00','17:00:00',0,'199051c7-a77b-46b3-b1b5-c88fbee69c9c');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-08','09:30:00','17:00:00',2,'199051c7-a77b-46b3-b1b5-c88fbee69c9c');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-09','09:00:00','17:00:00',0,'199051c7-a77b-46b3-b1b5-c88fbee69c9c');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-10','09:00:00','17:00:00',2,'199051c7-a77b-46b3-b1b5-c88fbee69c9c');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-13','09:00:00','17:00:00',1,'199051c7-a77b-46b3-b1b5-c88fbee69c9c');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-14','08:00:00','17:00:00',0,'199051c7-a77b-46b3-b1b5-c88fbee69c9c');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-15','08:00:00','17:00:00',1,'199051c7-a77b-46b3-b1b5-c88fbee69c9c');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-16','09:00:00','17:00:00',1,'199051c7-a77b-46b3-b1b5-c88fbee69c9c');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-17','08:00:00','17:00:00',2,'199051c7-a77b-46b3-b1b5-c88fbee69c9c');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-01','09:00:00','17:00:00',2,'199051c7-a77b-46b3-b1b5-c88fbee69c9c');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-20','09:30:00','17:00:00',2,'199051c7-a77b-46b3-b1b5-c88fbee69c9c');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-21','09:00:00','17:00:00',1,'199051c7-a77b-46b3-b1b5-c88fbee69c9c');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-22','09:00:00','17:00:00',2,'199051c7-a77b-46b3-b1b5-c88fbee69c9c');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-23','08:00:00','17:00:00',2,'199051c7-a77b-46b3-b1b5-c88fbee69c9c');

INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-01','09:00:00','17:00:00',1,'a6a206ce-8c08-45cd-8bf2-cd60cb2901a9');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-02','08:00:00','17:00:00',0,'a6a206ce-8c08-45cd-8bf2-cd60cb2901a9');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-03','09:00:00','16:00:00',2,'a6a206ce-8c08-45cd-8bf2-cd60cb2901a9');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-06','10:00:00','17:00:00',0,'a6a206ce-8c08-45cd-8bf2-cd60cb2901a9');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-07','09:00:00','17:00:00',0,'a6a206ce-8c08-45cd-8bf2-cd60cb2901a9');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-08','09:30:00','17:00:00',2,'a6a206ce-8c08-45cd-8bf2-cd60cb2901a9');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-09','09:00:00','17:00:00',0,'a6a206ce-8c08-45cd-8bf2-cd60cb2901a9');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-10','09:00:00','17:00:00',2,'a6a206ce-8c08-45cd-8bf2-cd60cb2901a9');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-13','09:00:00','17:00:00',1,'a6a206ce-8c08-45cd-8bf2-cd60cb2901a9');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-14','08:00:00','17:00:00',0,'a6a206ce-8c08-45cd-8bf2-cd60cb2901a9');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-15','08:00:00','17:00:00',1,'a6a206ce-8c08-45cd-8bf2-cd60cb2901a9');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-16','09:00:00','17:00:00',1,'a6a206ce-8c08-45cd-8bf2-cd60cb2901a9');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-17','08:00:00','17:00:00',2,'a6a206ce-8c08-45cd-8bf2-cd60cb2901a9');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-01','09:00:00','17:00:00',2,'a6a206ce-8c08-45cd-8bf2-cd60cb2901a9');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-20','09:30:00','17:00:00',2,'a6a206ce-8c08-45cd-8bf2-cd60cb2901a9');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-21','09:00:00','17:00:00',1,'a6a206ce-8c08-45cd-8bf2-cd60cb2901a9');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-22','09:00:00','17:00:00',2,'a6a206ce-8c08-45cd-8bf2-cd60cb2901a9');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-23','08:00:00','17:00:00',2,'a6a206ce-8c08-45cd-8bf2-cd60cb2901a9');

INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-14','08:00:00','17:00:00',0,'2af7c82d-cf08-46b4-9592-99cc0b6f9c66');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-15','08:00:00','17:00:00',1,'2af7c82d-cf08-46b4-9592-99cc0b6f9c66');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-16','09:00:00','17:00:00',1,'2af7c82d-cf08-46b4-9592-99cc0b6f9c66');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-17','08:00:00','17:00:00',2,'2af7c82d-cf08-46b4-9592-99cc0b6f9c66');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-01','09:00:00','17:00:00',2,'2af7c82d-cf08-46b4-9592-99cc0b6f9c66');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-20','09:30:00','17:00:00',2,'2af7c82d-cf08-46b4-9592-99cc0b6f9c66');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-21','09:00:00','17:00:00',1,'2af7c82d-cf08-46b4-9592-99cc0b6f9c66');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-22','09:00:00','17:00:00',2,'2af7c82d-cf08-46b4-9592-99cc0b6f9c66');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-23','08:00:00','17:00:00',2,'2af7c82d-cf08-46b4-9592-99cc0b6f9c66');

INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-14','08:00:00','17:00:00',0,'1437ed92-ec73-4a10-933e-4d103b662f6c');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-15','08:00:00','17:00:00',1,'1437ed92-ec73-4a10-933e-4d103b662f6c');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-16','09:00:00','17:00:00',1,'1437ed92-ec73-4a10-933e-4d103b662f6c');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-17','08:00:00','17:00:00',2,'1437ed92-ec73-4a10-933e-4d103b662f6c');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-01','09:00:00','17:00:00',2,'1437ed92-ec73-4a10-933e-4d103b662f6c');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-20','09:30:00','17:00:00',2,'1437ed92-ec73-4a10-933e-4d103b662f6c');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-21','09:00:00','17:00:00',1,'1437ed92-ec73-4a10-933e-4d103b662f6c');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-22','09:00:00','17:00:00',2,'1437ed92-ec73-4a10-933e-4d103b662f6c');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-23','08:00:00','17:00:00',2,'1437ed92-ec73-4a10-933e-4d103b662f6c');

INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-14','08:00:00','17:00:00',0,'e6b1ae3f-d566-4b13-a245-e86ee26ec42e');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-15','08:00:00','17:00:00',1,'e6b1ae3f-d566-4b13-a245-e86ee26ec42e');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-16','09:00:00','17:00:00',1,'e6b1ae3f-d566-4b13-a245-e86ee26ec42e');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-17','08:00:00','17:00:00',2,'e6b1ae3f-d566-4b13-a245-e86ee26ec42e');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-01','09:00:00','17:00:00',2,'e6b1ae3f-d566-4b13-a245-e86ee26ec42e');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-20','09:30:00','17:00:00',2,'e6b1ae3f-d566-4b13-a245-e86ee26ec42e');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-21','09:00:00','17:00:00',1,'e6b1ae3f-d566-4b13-a245-e86ee26ec42e');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-22','09:00:00','17:00:00',2,'e6b1ae3f-d566-4b13-a245-e86ee26ec42e');
INSERT INTO public.work_time_reports ("date",start_time,end_time,absence_reason,contract_id) VALUES
	 ('2023-11-23','08:00:00','17:00:00',2,'e6b1ae3f-d566-4b13-a245-e86ee26ec42e');
