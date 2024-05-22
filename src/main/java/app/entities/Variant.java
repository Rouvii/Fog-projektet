package app.entities;

/**
 * Purpose:
 *
 * @author: Kevin Løvstad Schou,Mathias Sigurdsson
 */
public class Variant
{
    private int variantId;
    private Materialer materiale;
    private int length;


    public Variant(int variantId, Materialer materiale, int length)
    {
        this.variantId = variantId;
        this.materiale = materiale;
        this.length = length;
    }




    public int getVariantId() {
        return variantId;
    }

    public Materialer getMateriale() {
        return materiale;
    }

    public int getLength() {
        return length;
    }
}
