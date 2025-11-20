CREATE TABLE business_calendar
(
    basis_date          DATE PRIMARY KEY,
    previous_basis_date DATE      NOT NULL,
    next_basis_date     DATE      NOT NULL,
    start_of_month      DATE      NOT NULL,
    end_of_month        DATE      NOT NULL,
    business_date_flag  BOOLEAN   NOT NULL,
    created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by          TEXT      NOT NULL,
    updated_at          TIMESTAMP,
    updated_by          TEXT,
    version             INT       NOT NULL DEFAULT 0
);
COMMENT ON TABLE business_calendar IS '営業日カレンダー';

COMMENT ON COLUMN business_calendar.basis_date IS '基準日';
COMMENT ON COLUMN business_calendar.previous_basis_date IS '前基準日';
COMMENT ON COLUMN business_calendar.next_basis_date IS '翌基準日';
COMMENT ON COLUMN business_calendar.start_of_month IS '月初基準日';
COMMENT ON COLUMN business_calendar.end_of_month IS '月末基準日';
COMMENT ON COLUMN business_calendar.business_date_flag IS '営業日フラグ';
COMMENT ON COLUMN business_calendar.created_at IS '作成日時';
COMMENT ON COLUMN business_calendar.created_by IS '作成者';
COMMENT ON COLUMN business_calendar.updated_at IS '更新日時';
COMMENT ON COLUMN business_calendar.updated_by IS '更新者';
COMMENT ON COLUMN business_calendar.version IS 'バージョン';
