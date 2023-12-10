package attachments

/**
 * Информация для предварительного просмотра файла
 */
interface Preview {
    val type: String
}

/*

audio_message (object) — данные об аудиосообщении. Объект, который содержит следующие поля:
    duration (integer) — длительность аудиосообщения в секундах;
    waveform (array) — массив значений (integer) для визуального отображения звука;
    link_ogg (string) — URL .ogg-файла;
    link_mp3 (string) — URL .mp3-файла.
*/