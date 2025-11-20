CREATE TABLE batch_status
(
    code          VARCHAR(50) PRIMARY KEY,
    label         VARCHAR(50) NOT NULL,
    display_order INT         NOT NULL,
    description   TEXT
);
COMMENT ON TABLE batch_status IS 'バッチステータス';

CREATE TABLE batch_log
(
    id            UUID PRIMARY KEY,
    batch_id      VARCHAR(8)  NOT NULL,
    batch_name    VARCHAR(50) NOT NULL,
    batch_name_jp VARCHAR(50) NOT NULL,
    status        VARCHAR(10) NOT NULL,
    basis_date    DATE,
    exit_datetime TIMESTAMP,
    exit_message  TEXT,
    created_at    TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by    TEXT        NOT NULL,
    updated_at    TIMESTAMP,
    updated_by    TEXT,
    version       INT         NOT NULL DEFAULT 0,
    FOREIGN KEY (status) REFERENCES batch_status (code) ON DELETE RESTRICT ON UPDATE CASCADE
);
COMMENT ON TABLE batch_log IS 'バッチログ';

COMMENT ON COLUMN batch_log.id IS 'バッチログID';
COMMENT ON COLUMN batch_log.batch_id IS 'バッチID';
COMMENT ON COLUMN batch_log.batch_name IS 'バッチ名（物理）';
COMMENT ON COLUMN batch_log.batch_name_jp IS 'バッチ名（論理）';
COMMENT ON COLUMN batch_log.status IS 'バッチステータス';
COMMENT ON COLUMN batch_log.basis_date IS '基準日';
COMMENT ON COLUMN batch_log.exit_datetime IS '終了日時';
COMMENT ON COLUMN batch_log.exit_message IS '終了メッセージ';
COMMENT ON COLUMN batch_log.created_at IS '作成日時';
COMMENT ON COLUMN batch_log.created_by IS '作成者';
COMMENT ON COLUMN batch_log.updated_at IS '更新日時';
COMMENT ON COLUMN batch_log.updated_by IS '更新者';
COMMENT ON COLUMN batch_log.version IS 'バージョン';
