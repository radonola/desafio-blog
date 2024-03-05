package br.com.raissa.blog.util;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.http.HttpStatus;

import br.com.raissa.blog.exception.PostException;

public class ImageUtil {
	
	private static final String ERRO_SERVIDOR = "Erro no processamento da requisição";

	public static byte[] compressImage(byte[] data) {
		
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        
        byte[] tmp = new byte[4*1024];
        
        try {
        	while (!deflater.finished()) {
                int size = deflater.deflate(tmp);
                outputStream.write(tmp, 0, size);
            }
            
        	outputStream.close();
        } catch (Exception exception) {
        	
        	throw new PostException(String.format("Não foi possível comprimir a imagem. Mensagem: %s", exception.getLocalizedMessage()),
        			ERRO_SERVIDOR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return outputStream.toByteArray();
    }



    public static byte[] decompressImage(byte[] data) {
    	
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        
        byte[] tmp = new byte[4*1024];
        
        try {
        	while (!inflater.finished()) {
            	int count = inflater.inflate(tmp);
            	outputStream.write(tmp, 0, count);
            }
            
        	outputStream.close();
        } catch (Exception exception) {
        	
        	throw new PostException(String.format("Não foi possível descomprimir a imagem. Mensagem: %s", exception.getLocalizedMessage()),
        			ERRO_SERVIDOR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return outputStream.toByteArray();
    }
	
}
