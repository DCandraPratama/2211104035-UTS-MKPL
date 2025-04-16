package lib;

public class TaxFunction {

    // 🔁 Refactor 4: Ubah magic number jadi konstanta
    private static final int BASIC_NON_TAXABLE = 54000000;
    private static final int MARRIED_ADDITION = 4500000;
    private static final int CHILD_ADDITION = 1500000;
    private static final int MAX_CHILDREN = 3;
    private static final double TAX_RATE = 0.05;

    /**
     * Fungsi untuk menghitung jumlah pajak penghasilan pegawai yang harus dibayarkan setahun.
     * 
     * Pajak dihitung sebagai 5% dari penghasilan bersih tahunan (gaji dan pemasukan bulanan lainnya dikalikan jumlah bulan bekerja dikurangi pemotongan) dikurangi penghasilan tidak kena pajak.
     * 
     * Jika pegawai belum menikah dan belum punya anak maka penghasilan tidak kena pajaknya adalah Rp 54.000.000.
     * Jika pegawai sudah menikah maka penghasilan tidak kena pajaknya ditambah sebesar Rp 4.500.000.
     * Jika pegawai sudah memiliki anak maka penghasilan tidak kena pajaknya ditambah sebesar Rp 4.500.000 per anak sampai anak ketiga.
     */
    public static int calculateTax(TaxData data) {
        int tax = 0;

        // Validasi jumlah bulan kerja
        if (data.numberOfMonthWorking > 12) {
            System.err.println("More than 12 month working per year");
        }

        // 🔧 Refactor 5: Gunakan method bantu
        int validChildren = getMaxChildren(data.numberOfChildren);
        int nonTaxableIncome = calculateNonTaxableIncome(data.isMarried, validChildren);

        int annualNetIncome = ((data.monthlySalary + data.otherMonthlyIncome) * data.numberOfMonthWorking)
                - data.deductible - nonTaxableIncome;

        tax = (int) Math.round(TAX_RATE * annualNetIncome);
        return Math.max(tax, 0);
    }

    // 🔧 Method bantu 1
    private static int getMaxChildren(int children) {
        return Math.min(children, MAX_CHILDREN);
    }

    // 🔧 Method bantu 2
    private static int calculateNonTaxableIncome(boolean isMarried, int children) {
        int income = BASIC_NON_TAXABLE;
        if (isMarried) {
            income += MARRIED_ADDITION + (children * CHILD_ADDITION);
        }
        return income;
    }
}
