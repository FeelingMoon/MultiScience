package co.edu.unbosque.beans;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named("descargaBean")
@RequestScoped
public class DescargaBean {

	private StreamedContent file;

	@PostConstruct
	public void init() {
		file = DefaultStreamedContent.builder().name("plantilla.xlsx")
				.contentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
				.stream(() -> DescargaBean.class.getResourceAsStream("/co/edu/unbosque/persistence/plantilla.xlsx"))
				.build();
	}

	public StreamedContent getFile() {
		return file;
	}

	public void setFile(StreamedContent file) {
		this.file = file;
	}

}
