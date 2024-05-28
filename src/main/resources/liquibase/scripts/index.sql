-- liquibase formatted sql

-- changeset asemenikhin:1
CREATE INDEX st_name_index ON students (name);

-- changeset asemenikhin:2
CREATE INDEX fc_name_color_inx ON faculties (name, color);