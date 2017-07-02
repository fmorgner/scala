.PHONY: all clean dist-clean

DOCUMENT=scala
export DOCUMENT

QPDF:=$(shell command -v qpdf 2>/dev/null)

all: copy
	@echo "Build complete!"
	@echo	"PDF located at: '$(shell pwd)/generated/$(DOCUMENT).pdf'"

clean:
	@echo "Cleaning LaTeX artifacts..."
	@$(MAKE) -C latex clean
	@echo "Removing generated PDF files..."
	@rm -f generated/pdf/*.pdf

dist-clean: clean
	@echo "Removing untracked files..."
	@git clean -fdx &>/dev/null

compile: dirs
	@echo "Building LaTeX document..."
	@$(MAKE) -C latex

copy: compile
ifdef QPDF
	@echo "Linearizing PDF..."
	@qpdf --linearize latex/.build/$(DOCUMENT).pdf generated/$(DOCUMENT).pdf
else
	@echo "Copying PDF..."
	@cp latex/.build/$(DOCUMENT).pdf generated/
endif

dirs:
	@mkdir -p generated
