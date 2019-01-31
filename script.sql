create table Brand
(
  id   int auto_increment
    primary key,
  name varchar(45) not null
);

create table Kind
(
  id   int auto_increment
    primary key,
  name varchar(45) not null
);

create table Alcohol
(
  id                int auto_increment
    primary key,
  alcoholicStrength double      null,
  name              varchar(45) not null,
  volume            int         not null,
  brand_id          int         not null,
  kind_id           int         not null,
  constraint FK2sdfrejsrdcnqcp804gcffkte
    foreign key (brand_id) references Brand (id),
  constraint FK4p9fme79ew5l0mlusmhjmy6x5
    foreign key (kind_id) references Kind (id)
);

create table Shop
(
  id   int auto_increment
    primary key,
  name varchar(45) not null
);

create table User
(
  id          int auto_increment
    primary key,
  isAdmin     bit          not null,
  login       varchar(45)  not null,
  password    varchar(300) not null,
  email       varchar(60)  not null,
  isActivated bit          not null,
  role        varchar(20)  not null,
  constraint email
    unique (email),
  constraint login
    unique (login)
);

create table Sale
(
  id            int auto_increment
    primary key,
  price         double null,
  negativeRates int    not null,
  positiveRates int    not null,
  alcohol_id    int    not null,
  shop_id       int    not null,
  user_id       int    not null,
  constraint FKbl6ax95xgdo5w16cmsv3tpnvm
    foreign key (shop_id) references Shop (id),
  constraint FKghx4gde0kjosy44et0a73oxtd
    foreign key (alcohol_id) references Alcohol (id),
  constraint FKwyJaUVv4VbmefzLFpPvHKLHu9
    foreign key (user_id) references User (id)
);

create table UserOpinion
(
  id      int auto_increment
    primary key,
  user_id int not null,
  sale_id int not null,
  opinion int not null,
  constraint CC2VHJxrLkGEe7nc45Um75pDQHX
    foreign key (user_id) references User (id),
  constraint upcU6b5sTdrE4yxPo3s2SkUZzm3
    foreign key (sale_id) references Sale (id)
);

DELIMITER $$
create trigger new_opinion
  after INSERT
  on UserOpinion
  for each row
BEGIN
  IF (NEW.opinion = 1) THEN
    UPDATE Sale SET Sale.positiveRates = Sale.positiveRates + 1 WHERE Sale.id = NEW.Sale_id;
  ELSE
    UPDATE Sale SET Sale.negativeRates = Sale.negativeRates + 1 WHERE Sale.id = NEW.Sale_id;
  END IF;
END;$$
DELIMITER ;

DELIMITER $$
create trigger update_opinion
  after UPDATE
  on UserOpinion
  for each row
BEGIN
  IF EXISTS(
      SELECT * FROM UserOpinion WHERE UserOpinion.user_id = NEW.user_id AND UserOpinion.Sale_id = NEW.Sale_id) THEN
    IF (NEW.opinion = 1) THEN
      UPDATE Sale SET Sale.negativeRates = Sale.negativeRates - 1 WHERE Sale.id = NEW.Sale_id;
    ELSE
      UPDATE Sale SET Sale.positiveRates = Sale.positiveRates - 1 WHERE Sale.id = NEW.Sale_id;
    END IF;
  END IF;

  IF (NEW.opinion = 1) THEN
    UPDATE Sale SET Sale.positiveRates = Sale.positiveRates + 1 WHERE Sale.id = NEW.Sale_id;
  ELSE
    UPDATE Sale SET Sale.negativeRates = Sale.negativeRates + 1 WHERE Sale.id = NEW.Sale_id;
  END IF;
END;$$
DELIMITER ;

create table VerificationToken
(
  id      int auto_increment
    primary key,
  token   varchar(60) not null,
  user_id int         not null,
  time    timestamp   not null,
  constraint wbzxVMfrTjZKH4T6F9aAAPxjoSd
    foreign key (user_id) references User (id)
);