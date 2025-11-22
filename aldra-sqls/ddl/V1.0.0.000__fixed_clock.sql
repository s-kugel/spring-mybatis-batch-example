CREATE TABLE fixed_clock
(
    base_time  TIMESTAMP PRIMARY KEY,
    time_zone  TEXT      NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by TEXT      NOT NULL,
    updated_at TIMESTAMP,
    updated_by TEXT,
    version    INT       NOT NULL DEFAULT 0
);
COMMENT ON TABLE fixed_clock IS '固定時刻設定';

COMMENT ON COLUMN fixed_clock.base_time IS '時刻';
COMMENT ON COLUMN fixed_clock.time_zone IS 'タイムゾーン';
COMMENT ON COLUMN fixed_clock.created_at IS '作成日時';
COMMENT ON COLUMN fixed_clock.created_by IS '作成者';
COMMENT ON COLUMN fixed_clock.updated_at IS '更新日時';
COMMENT ON COLUMN fixed_clock.updated_by IS '更新者';
COMMENT ON COLUMN fixed_clock.version IS 'バージョン';
