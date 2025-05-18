CREATE EXTENSION IF NOT EXISTS postgis;

-- Таблица достопримечательностей
CREATE TABLE sight (
    id BIGSERIAL PRIMARY KEY,
    name TEXT UNIQUE NOT NULL,
    city TEXT NOT NULL,
    position GEOGRAPHY(Point, 4326) NOT NULL,
    category TEXT NOT NULL
);

-- Индекс для геопоиска
CREATE INDEX idx_sight_position ON sight USING GIST (position);

-- Таблица отзывов
CREATE TABLE feedback (
    id BIGSERIAL PRIMARY KEY,
    sight_id BIGINT NOT NULL REFERENCES sight(id) ON DELETE CASCADE,
    username TEXT NOT NULL,
    text TEXT,
    estimation INT NOT NULL CHECK (estimation BETWEEN 1 AND 5)
);