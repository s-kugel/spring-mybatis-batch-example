CREATE TABLE application_date
(
    basis_date DATE PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by TEXT      NOT NULL,
    updated_at TIMESTAMP,
    updated_by TEXT,
    version    INT       NOT NULL DEFAULT 0
);
COMMENT ON TABLE application_date IS 'アプリケーション日付';

COMMENT ON COLUMN application_date.basis_date IS '基準日';
COMMENT ON COLUMN application_date.created_at IS '作成日時';
COMMENT ON COLUMN application_date.created_by IS '作成者';
COMMENT ON COLUMN application_date.updated_at IS '更新日時';
COMMENT ON COLUMN application_date.updated_by IS '更新者';
COMMENT ON COLUMN application_date.version IS 'バージョン';
