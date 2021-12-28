package classes;


import java.util.Arrays;
import java.util.Objects;

public class Genes {
    private final int[] genotype;
    private final int size;
    private final int geneCount;

    public Genes(int size, int geneCount) {
        this.size = size;
        this.geneCount = geneCount;
        this.genotype = new int[size];
        generateRandomGenes();
    }

    public Genes(Genes g1, Genes g2, float part) {
//        g1 - genes of stronger parent
        int side = 0;
        int[] genotype1 = g1.getGenotype();
        int[] genotype2 = g2.getGenotype();
        this.size = genotype1.length;
        this.genotype = new int[size];
        this.geneCount = g1.getGeneCount();
        if (Math.random() > 0.5) {
            side = 1;
        }
        if (side == 0) {
//            select left% genes from the stronger parent
            int lAmount = (int) Math.ceil(size * part);

            for (int i = 0; i < this.size; i++) {
                if (i <= lAmount) {
                    this.genotype[i] = genotype1[i];
                } else {
                    this.genotype[i] = genotype2[i];
                }
            }
        } else {
            int lAmount = this.size - (int) Math.floor(size * part);
            for (int i = 0; i < this.size; i++) {
                if (i <= lAmount) {
                    this.genotype[i] = genotype2[i];
                } else {
                    this.genotype[i] = genotype1[i];
                }
            }
        }
        Arrays.sort(this.genotype);
    }

    private void generateRandomGenes() {
        for (int i = 0; i < this.size; i++) {
            this.genotype[i] = (int) Math.floor(Math.random() * this.geneCount);
        }
        Arrays.sort(this.genotype);
    }

    public int getGeneCount() {
        return geneCount;
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Genes that))
            return false;
        return Arrays.equals(this.genotype, that.genotype);
    }

    public int hashCode() {
        return Objects.hash(this.genotype);
    }

    public int[] getGenotype() {
        return genotype;
    }

    public int getMove() {
        return this.genotype[(int) Math.floor(Math.random() * this.size)];
    }

}
