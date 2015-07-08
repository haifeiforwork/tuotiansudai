BEGIN;
UPDATE bank_card b JOIN (SELECT DISTINCT
  t.`card_no`,t.`user_id`
FROM
  bank_card t
  JOIN bank_card m
    ON t.`user_id` = m.`user_id`
    AND t.`card_no` = m.`card_no`
    AND m.`status` = 'passed'
WHERE t.`status` <> 'passed') temp ON b.`card_no` = temp.`card_no` AND b.`user_id` = temp.`user_id`
SET b.`status` = 'passed';
COMMIT;