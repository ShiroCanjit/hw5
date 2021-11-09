CREATE TABLE organization
(
    id      SERIAL  NOT NULL,
    name    VARCHAR NOT NULL,
    inn     INT     NOT NULL,
    account INT     NOT NULL,
    CONSTRAINT organization_pk PRIMARY KEY (id)
);
CREATE TABLE waybill
(
    id          SERIAL  NOT NULL,
    number      INT     NOT NULL,
    date        DATE    NOT NULL,
    sender_id   INT     NOT NULL REFERENCES organization (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT waybill_pk PRIMARY KEY (id)
);
CREATE TABLE product
(
    id          SERIAL  NOT NULL,
    name        VARCHAR NOT NULL,
    code        INT     NOT NULL,
    CONSTRAINT product_pk PRIMARY KEY (id)
);
CREATE TABLE waybill_items
(
    id          SERIAL  NOT NULL,
    waybill_id  INT     NOT NULL REFERENCES waybill (id) ON UPDATE CASCADE ON DELETE CASCADE,
    price       INT     NOT NULL,
    product_id  INT     NOT NULL REFERENCES product (id) ON UPDATE CASCADE ON DELETE CASCADE,
    number      INT     NOT NULL,
    CONSTRAINT waybill_items_pk PRIMARY KEY (id)
);