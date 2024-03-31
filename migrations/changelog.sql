CREATE TABLE chat (
                      id BIGSERIAL PRIMARY KEY,
                      chat_id BIGINT NOT NULL
);

CREATE TABLE link (
                      id BIGSERIAL PRIMARY KEY,
                      url TEXT NOT NULL,
                      chat_id BIGINT NOT NULL,
                      last_check_time TIMESTAMP WITH TIME ZONE NOT NULL,
                      created_at TIMESTAMP WITH TIME ZONE NOT NULL
);
