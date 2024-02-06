create table produtos (

    id bigint not null auto_increment,
    codigo_de_Barras varchar(200) not null,
    categoria bigint not null,
    produto varchar(500) not null,

    sabor varchar(100) not null,
    idade varchar(100) not null,
    preco DECIMAL not null,
    peso DECIMAL not null,
    desconto DECIMAL not null,
    animal varchar(20) not null,
    castrado int not null,


    fornecedor varchar(100) not null,
    estoque INT not null,
    imagemP varchar(1000) not null,

    primary key(id),
    FOREIGN KEY (categoria) REFERENCES categoria(id)

);