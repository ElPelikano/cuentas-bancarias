package edu.tallerweb.cuentas;

/**
 * La más compleja de las cuentas, ésta permite establecer una
 * cantidad de dinero a girar en descubierto. Es por ello que
 * cada vez que se desee extraer dinero, no sólo se considera
 * el que se posee, sino el límite adicional que el banco
 * estará brindando.
 *
 * Por supuesto esto no es gratis, ya que el banco nos cobrará
 * un 5% como comisión sobre todo el monto en descubierto
 * consumido en la operación.
 *
 * Por ejemplo, si tuviéramos $ 100 en la cuenta, y quisiéramos
 * retirar $ 200 (con un descubierto de $ 150), podremos hacerlo.
 * Pasaremos a deberle al banco $ 105 en total: los $ 100 que
 * nos cubrió, más el 5% adicional sobre el descubierto otorgado.
 */
public class CuentaCorriente extends AbstractCuenta {

	private Double descubiertoTotal;
	private Double descubiertoDeLaCuenta;
	
	private static final Double COMISION = 1.05;
	
	/**
	 * Toda cuenta corriente se inicia con un límite total
	 * para el descubierto.
	 * @param descubiertoTotal
	 */
	public CuentaCorriente(final Double descubiertoTotal) {
		super();

		this.descubiertoTotal = descubiertoTotal;
		this.descubiertoDeLaCuenta = descubiertoTotal;
		//throw new RuntimeException("No implementado aún");
	}
	
	/**
	 * Todo depósito deberá cubrir primero el descubierto,
	 * si lo hubiera, y luego contar para el saldo de la
	 * cuenta.
	 * @param monto a depositar
	 */
	public void depositar(final Double monto) {
		if (monto > CERO) 
		{
			if (this.descubiertoTotal == this.descubiertoDeLaCuenta) 
			{
				this.saldo += monto;
			
			} else {
				if (monto >= (this.descubiertoDeLaCuenta - this.descubiertoTotal)) 
				{
					  this.saldo = monto - (this.descubiertoDeLaCuenta - this.descubiertoTotal);
					  this.descubiertoTotal = this.descubiertoDeLaCuenta;
				
				} else {
					this.descubiertoTotal += monto;
				}   // if (monto >= (this.descubiertoDeLaCuenta - this.descubiertoTotal))
			}   // if (this.descubiertoTotal == this.descubiertoDeLaCuenta)
		
		} else {
			throw new CuentaBancariaException("Debe depositar un monto mayor a 0.");
		}   // if (monto > CERO)
	}
	
	/**
	 * Se cobrará el 5% de comisión sobre el monto girado
	 * en descubierto.
	 * Por supuesto, no puede extraerse más que el total
	 * de la cuenta, más el descubierto (comisión incluída)
	 * @param monto a extraer
	 */
	public void extraer(final Double monto) {
		 if (monto > CERO) 
		 {
			 if (this.saldo >= monto) 
			 {
			 
				 this.saldo -= monto;
			 } else {
				 if (this.saldo < monto) 
				 {
					  if (monto <= (this.saldo + (this.descubiertoTotal / COMISION))) 
					  {
						  this.descubiertoTotal -= ((monto - this.saldo) * COMISION);
						  this.saldo = CERO;
					  
					  } else {
						  throw new CuentaBancariaException("No posee saldo para extraer");
					  }   // if (monto <= (this.saldo + (this.descubiertoTotal / COMISION)))
				 }   // if (this.saldo < monto)
			 }   // if (this.saldo >= monto)
		 
		 } else {
			 throw new CuentaBancariaException("El monto a extraer debe ser mayor a 0.");
		 }   // if (monto > CERO)
	}
	
	/**
	 * Permite saber el saldo de la cuenta
	 * @return el saldo de la cuenta
	 */
	public Double getSaldo() {
		return this.saldo;
	}

	/**
	 * Permite saber el saldo en descubierto
	 * @return el descubierto de la cuenta
	 */
	public Double getDescubierto() {
		return this.descubiertoTotal;
	}

}
