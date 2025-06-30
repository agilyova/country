CREATE
EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS "country"
(
    id
    UUID
    unique
    not
    null
    default
    uuid_generate_v1
(
) primary key,
    name varchar
(
    50
) UNIQUE NOT NULL,
    code char
(
    2
) UNIQUE NOT NULL,
    CONSTRAINT code_length CHECK
(
    char_length
(
    code
) = 2)
    );

INSERT INTO country (name, code)
VALUES ('Fiji', 'FJ'),
       ('Tanzania', 'TZ'),
       ('Western Sahara', 'EH'),
       ('Canada', 'CA'),
       ('United States', 'US'),
       ('Kazakhstan', 'KZ'),
       ('Uzbekistan', 'UZ'),
       ('Papua New Guinea', 'PG'),
       ('Indonesia', 'ID'),
       ('Argentina', 'AR'),
       ('Chile', 'CL'),
       ('Democratic Republic of the Congo', 'CD'),
       ('Somalia', 'SO'),
       ('Kenya', 'KE'),
       ('Sudan', 'SD'),
       ('Chad', 'TD'),
       ('Haiti', 'HT'),
       ('Dominican Republic', 'DO'),
       ('Russia', 'RU'),
       ('Bahamas', 'BS'),
       ('Falkland Islands', 'FK'),
       ('Norway', 'NO'),
       ('Greenland', 'GL'),
       ('Timor-Leste', 'TL'),
       ('South Africa', 'ZA'),
       ('Lesotho', 'LS'),
       ('Mexico', 'MX'),
       ('Uruguay', 'UY'),
       ('Brazil', 'BR'),
       ('Bolivia', 'BO'),
       ('Peru', 'PE'),
       ('Colombia', 'CO'),
       ('Panama', 'PA'),
       ('Costa Rica', 'CR'),
       ('Nicaragua', 'NI'),
       ('Honduras', 'HN'),
       ('El Salvador', 'SV'),
       ('Guatemala', 'GT'),
       ('Belize', 'BZ'),
       ('Venezuela', 'VE'),
       ('Guyana', 'GY'),
       ('Suriname', 'SR'),
       ('France', 'FR'),
       ('Ecuador', 'EC'),
       ('Puerto Rico', 'PR'),
       ('Jamaica', 'JM'),
       ('Cuba', 'CU'),
       ('Zimbabwe', 'ZW'),
       ('Botswana', 'BW'),
       ('Namibia', 'NA'),
       ('Senegal', 'SN'),
       ('Mali', 'ML'),
       ('Benin', 'BJ'),
       ('Niger', 'NE'),
       ('Nigeria', 'NG'),
       ('Cameroon', 'CM'),
       ('Togo', 'TG'),
       ('Ghana', 'GH'),
       ('Côted''Ivoire', 'CI'),
       ('Guinea', 'GN'),
       ('Guinea-Bissau', 'GW'),
       ('Liberia', 'LR'),
       ('Sierra Leone', 'SL'),
       ('Burkina Faso', 'BF'),
       ('Central African Republic', 'CF'),
       ('Republic of the Congo', 'CG'),
       ('Gabon', 'GA'),
       ('Equatorial Guinea', 'GQ'),
       ('Zambia', 'ZM'),
       ('Malawi', 'MW'),
       ('Mozambique', 'MZ'),
       ('Eswatini', 'SZ'),
       ('Angola', 'AO'),
       ('Burundi', 'BI'),
       ('Israel', 'IL'),
       ('Lebanon', 'LB'),
       ('Madagascar', 'MG'),
       ('Palestine', 'PS'),
       ('The Gambia', 'GM'),
       ('Tunisia', 'TN'),
       ('Algeria', 'DZ'),
       ('Jordan', 'JO'),
       ('United Arab Emirates', 'AE'),
       ('Qatar', 'QA'),
       ('Kuwait', 'KW'),
       ('Iraq', 'IQ'),
       ('Oman', 'OM'),
       ('Vanuatu', 'VU'),
       ('Cambodia', 'KH'),
       ('Thailand', 'TH'),
       ('Lao PDR', 'LA'),
       ('Myanmar', 'MM'),
       ('Vietnam', 'VN'),
       ('Dem. Rep. Korea', 'KP'),
       ('Republic of Korea', 'KR'),
       ('Mongolia', 'MN'),
       ('India', 'IN');
