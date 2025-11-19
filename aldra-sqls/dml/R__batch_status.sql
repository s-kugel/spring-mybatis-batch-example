INSERT INTO batch_status (code, label, display_order, description)
VALUES
    ('RUNNING', '実行中', 1, NULL),
    ('WARN', '警告終了', 2, NULL),
    ('SUCCESS', '正常終了', 3, NULL),
    ('FAIL', '異常終了', 4, NULL)
ON CONFLICT (code)
DO UPDATE SET
   label = EXCLUDED.label,
   display_order = EXCLUDED.display_order,
   description = EXCLUDED.description
;
