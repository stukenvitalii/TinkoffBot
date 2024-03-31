CREATE TABLE sof_link_properties
(
    id            SERIAL PRIMARY KEY,
    link_id       BIGINT NOT NULL,
    answer_count  BIGINT NOT NULL,
    comment_count BIGINT NOT NULL
);
