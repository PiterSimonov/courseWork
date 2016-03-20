DELIMITER //
CREATE TRIGGER commentIns AFTER INSERT ON comment
FOR EACH ROW
  BEGIN
    UPDATE hotel h
    SET rating = (SELECT avg(rating)
                  FROM comment
                  WHERE hotel_id = h.id)
    WHERE new.hotel_id = h.id;
  END
//

DELIMITER //
CREATE TRIGGER commentUpd AFTER UPDATE ON comment
FOR EACH ROW
  BEGIN
    UPDATE hotel h
    SET rating = (SELECT avg(rating)
                  FROM comment
                  WHERE hotel_id = h.id)
    WHERE new.hotel_id = h.id;
  END
//

DELIMITER //
CREATE TRIGGER commentDelete AFTER DELETE ON comment
FOR EACH ROW
  BEGIN
    UPDATE hotel h
    SET rating = (SELECT avg(rating)
                  FROM comment
                  WHERE hotel_id = h.id)
    WHERE old.hotel_id = h.id;
  END
//