package pl.zgora.uz.wiea.pkdg.common;

public interface Converter<E, M> {
	M convertEntityToModel(E entity);

	E convertModelToEntity(M model);
}
