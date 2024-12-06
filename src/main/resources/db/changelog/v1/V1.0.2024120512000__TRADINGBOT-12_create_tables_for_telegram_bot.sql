--liquibase formatted sql

--changeset mzhikha:1
create table chat_data
(
    id        uuid primary key default gen_v7_uuid_seq(),
    chat_id      bigserial,
    created_at timestamptz  not null default now()
);
--rollback drop table stock;

