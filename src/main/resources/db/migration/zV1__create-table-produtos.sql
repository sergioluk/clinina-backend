create table produtos(

    id bigint not null auto_increment,
    produto varchar(500) not null,
    sabor varchar(100) not null,
    idade varchar(100) not null,
    peso DECIMAL not null,
    castrado INT not null,
    estoque INT not null,
    venda INT not null,
    informacao varchar(1000) not null,
    animal varchar(20) not null,
    preco DECIMAL not null,
    desconto DECIMAL not null,
    fornecedor varchar(100) not null,
    litros varchar(100) not null,
    categoria varchar(100) not null,
    imagemP varchar(1000) not null,
    imagens varchar(4000) not null,







    primary key(id)

);