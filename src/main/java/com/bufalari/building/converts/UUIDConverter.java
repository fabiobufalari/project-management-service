package com.bufalari.building.converts;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * JPA AttributeConverter para converter UUID para byte[] e vice-versa.
 * Útil se você quiser armazenar UUIDs como BYTEA no banco para potencial otimização de índice,
 * em vez do tipo UUID nativo do PostgreSQL.
 * Se estiver usando o tipo UUID nativo do PostgreSQL (com @Column(columnDefinition="uuid")),
 * este conversor não é estritamente necessário, a menos que você queira forçar
 * um formato específico de bytes.
 */
@Converter(autoApply = false) // Defina como true se quiser aplicar globalmente a todos os UUIDs. Cuidado!
public class UUIDConverter implements AttributeConverter<UUID, byte[]> {

    @Override
    public byte[] convertToDatabaseColumn(UUID uuid) {
        if (uuid == null) {
            return null;
        }
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }

    @Override
    public UUID convertToEntityAttribute(byte[] bytes) {
        if (bytes == null || bytes.length != 16) { // Adicionar verificação de tamanho
            // Retornar null ou lançar exceção se o array de bytes for inválido
            // Se bytes for nulo, retornar nulo é apropriado.
            // Se bytes não for nulo mas tiver tamanho incorreto, pode ser um erro.
            if (bytes == null) return null;
            throw new IllegalArgumentException("Byte array for UUID must be 16 bytes long.");
        }
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        return new UUID(bb.getLong(), bb.getLong());
    }
}