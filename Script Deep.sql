CREATE TABLE clientes (
    idCliente SERIAL PRIMARY KEY,
    nome VARCHAR(250),
    cpf VARCHAR(14)
);
CREATE TABLE carros (
    id_carro SERIAL PRIMARY KEY,
    nome_carro VARCHAR(100),
    id_cliente INTEGER,
    placa VARCHAR(10),
    data_entrada DATE,
    hora_entrada TIME,
    data_saida DATE,
    hora_saida TIME,
    foreign key (id_cliente) REFERENCES clientes(idCliente)
);
CREATE TABLE permanencias (
    id_permanencia SERIAL PRIMARY KEY,
    idCarro INTEGER,
    valor DECIMAL(10,2),
    foreign key (idCarro)  REFERENCES carros( id_carro )
);

insert into clientes (nome,cpf) values('Muryllo','12345678910');

insert into carros (nome_carro,id_cliente,placa,data_entrada,hora_entrada,
data_saida,hora_saida
) values 
('Ford',1,'XDRGH','2023-07-24','12:00','2023-07-24','17:00');

insert into  permanencias(idCarro,valor) 
values (1,90);



select * from clientes;
select * from carros;
select * from permanencias ;

select distinct  nome,cpf,data_entrada,data_saida,placa,hora_entrada,hora_saida,valor  from permanencias
inner join carros 
on id_permanencia=id_carro
inner  join clientes 
on id_permanencia  =idCliente
where idcarro =2
;


--drop  table permanencias;
--drop  table carros;
--drop  table clientes;





