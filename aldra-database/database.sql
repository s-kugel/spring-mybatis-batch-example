CREATE TABLE batch_exit_status
(
    code          VARCHAR(50) PRIMARY KEY,
    label         VARCHAR(50) NOT NULL,
    display_order INT         NOT NULL,
    description   TEXT
);
COMMENT ON TABLE batch_exit_status IS 'バッチ終了ステータス';

CREATE TABLE batch_log
(
    id            VARCHAR(26) PRIMARY KEY,
    batch_id      VARCHAR(8)  NOT NULL,
    batch_name    VARCHAR(50) NOT NULL,
    batch_name_jp VARCHAR(50) NOT NULL,
    status        VARCHAR(10) NOT NULL,
    exit_datetime TIMESTAMP,
    exit_message  TEXT,
    created_at    TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by    VARCHAR(26) NOT NULL,
    updated_at    TIMESTAMP,
    updated_by    VARCHAR(26),
    version       INT         NOT NULL DEFAULT 0,
    FOREIGN KEY (status) REFERENCES batch_exit_status (code) ON DELETE RESTRICT ON UPDATE CASCADE
);
